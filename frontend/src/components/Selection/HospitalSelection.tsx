/* eslint-disable react-hooks/exhaustive-deps */
import { ChangeEvent, useCallback, useEffect, useState } from "react";
import styles from "@/pages/Home/Home.module.css";
import { Hospital } from "@/utils/types";
import {
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { observer } from "mobx-react-lite";
import { autorun } from "mobx";

export const HospitalSelection = observer(() => {
  const [filteredHospitals, setFilteredHospitals] = useState<Hospital[]>([]);
  const clientToken = useClientToken();
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const handleChange = useCallback((e: ChangeEvent<HTMLSelectElement>) => {
    const selectedHospitalId = parseInt(e.target.value, 10);
    selectionStore.selectedHospitalId = selectedHospitalId;
      selectionStore.selectedDirectionId = -1;
      selectionStore.selectedDoctorId = -1;
  }, []);

  useEffect(
    () =>
      autorun(() => {
        if (selectionStore.selectedDistrictId !== -1) {
          const filtered = globalStore.hospitals.filter(
            (hospital: Hospital) =>
              selectionStore.selectedDistrictId === hospital.districtId
          );

          setFilteredHospitals(filtered);
        } else {
          selectionStore.selectedHospitalId = -1;
          setFilteredHospitals([]);
        }
      }),
    [clientToken]
  );

  return (
    <div className={styles.form_section}>
      <label htmlFor="hospital" className={styles.label}>
        Больница:
      </label>
      <select
        name="hospital"
        id="hospitalSelect"
        onChange={handleChange}
        value={selectionStore.selectedHospitalId}
        disabled={selectionStore.selectedDistrictId === -1}
      >
        <option value="-1">Выберите учреждение</option>
        {filteredHospitals.map((hospital: Hospital) => (
          <option key={hospital.gorzdravId} value={hospital.gorzdravId}>
            {hospital.shortName}
          </option>
        ))}
      </select>
    </div>
  );
});
HospitalSelection.displayName = "HospitalSelection";
