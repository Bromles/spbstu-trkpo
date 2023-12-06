import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { observer } from "mobx-react-lite";
import { useClientToken } from "@/utils/hooks";
import { GlobalStore } from "@/GlobalStore";
import { fetchDoctors } from "./SelectionApi";

type DoctorSelectionProps = {
  selectedDirectionId: number;
  selectedHospitalId: number;
  onDoctorChange: (selectedDoctorId: number) => void;
};

export const DoctorSelection = observer(
  ({
    selectedDirectionId,
    selectedHospitalId,
    onDoctorChange,
  }: DoctorSelectionProps) => {
    const clientToken = useClientToken();

    const handleChange = useCallback(
      (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDoctorId = parseInt(event.target.value, 10);
        onDoctorChange(selectedDoctorId);
      },
      [onDoctorChange]
    );

    useEffect(() => {
      const backendUrl = getBackendUrl();

      const fetchData = async () => {
        GlobalStore.doctors = await fetchDoctors(
          backendUrl,
          clientToken,
          selectedHospitalId,
          selectedDirectionId
        );
      };

      if (selectedDirectionId !== -1 && selectedHospitalId !== -1) {
        fetchData();
      } else {
        GlobalStore.doctors = [];
      }
    }, [selectedDirectionId, selectedHospitalId, clientToken]);

    return (
      <div className={styles.form_section}>
        <label htmlFor="doctor" className={styles.label}>
          Доктор:
        </label>
        <select name="doctor" id="doctorSelect" onChange={handleChange}>
          <option value="-1">Выберите доктора (опционально)</option>
          {GlobalStore.doctors.map((doctor) => (
            <option
              value={doctor.gorzdravId.toString()}
              key={doctor.gorzdravId}
            >
              {doctor.name}
            </option>
          ))}
        </select>
      </div>
    );
  }
);
