/* eslint-disable react-hooks/exhaustive-deps */
import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { observer } from "mobx-react-lite";
import {
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { fetchDoctors } from "./SelectionApi";
import { autorun } from "mobx";

export const DoctorSelection = observer(() => {
  const clientToken = useClientToken();
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const handleChange = useCallback((e: ChangeEvent<HTMLSelectElement>) => {
    const selectedDoctorId = parseInt(e.target.value, 10);
    selectionStore.selectedDoctorId = selectedDoctorId;
  }, []);

  useEffect(
    () =>
      autorun(() => {
        const backendUrl = getBackendUrl();

        const fetchData = async () => {
          globalStore.doctors = await fetchDoctors(
            backendUrl,
            clientToken,
            selectionStore.selectedHospitalId,
            selectionStore.selectedDirectionId
          );
        };

        if (
          selectionStore.selectedDirectionId !== -1 &&
          selectionStore.selectedHospitalId !== -1
        ) {
          fetchData();
        } else {
          globalStore.doctors = [];
          selectionStore.selectedDoctorId = -1;
        }
      }),
    [clientToken]
  );

  return (
    <div className={styles.form_section}>
      <label htmlFor="doctor" className={styles.label}>
        Доктор:
      </label>
      <select
        name="doctor"
        id="doctorSelect"
        onChange={handleChange}
        value={selectionStore.selectedDoctorId}
        disabled={selectionStore.selectedDirectionId === -1}
      >
        <option value="-1">Выберите доктора (опционально)</option>
        {globalStore.doctors.map((doctor) => (
          <option value={doctor.gorzdravId} key={doctor.gorzdravId}>
            {doctor.name}
          </option>
        ))}
      </select>
    </div>
  );
});
DoctorSelection.displayName = "DoctorSelection";
