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
import { autorun, trace } from "mobx";

export const DoctorSelection = observer(() => {
  trace();
  const clientToken = useClientToken();
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const handleChange = useCallback(
    (e: ChangeEvent<HTMLSelectElement>) => {
      const selectedDoctorId = parseInt(e.target.value, 10);
      selectionStore.selectedDoctorId = selectedDoctorId;
    },
    [selectionStore.selectedDoctorId]
  );

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
        defaultValue="-1"
      >
        <option value="-1">Выберите доктора (опционально)</option>
        {globalStore.doctors.map((doctor) => (
          <option value={doctor.gorzdravId.toString()} key={doctor.gorzdravId}>
            {doctor.name}
          </option>
        ))}
      </select>
    </div>
  );
});
DoctorSelection.displayName = "DoctorSelection";
