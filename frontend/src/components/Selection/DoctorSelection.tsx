import { ChangeEvent, useCallback, useEffect, useState } from "react";
import styles from "@/pages/Home/Home.module.css";
import { useAuth } from "react-oidc-context";
import { getBackendUrl } from "@/utils/apiUtils";
import { Doctor } from "@/utils/types";

type DoctorSelectionProps = {
  selectedDirectionId: number;
  selectedHospitalId: number;
  onDoctorChange: (selectedDoctorId: number) => void;
};

export const DoctorSelection = ({
  selectedDirectionId,
  selectedHospitalId,
  onDoctorChange,
}: DoctorSelectionProps) => {
  const [doctors, setDoctors] = useState<Doctor[]>([]);
  const auth = useAuth();

  const handleChange = useCallback(
    (event: ChangeEvent<HTMLSelectElement>) => {
      const selectedDoctorId = parseInt(event.target.value, 10);
      onDoctorChange(selectedDoctorId);
    },
    [onDoctorChange]
  );

  useEffect(() => {
    const backendURL = getBackendUrl();

    const fetchData = async () => {
      try {
        const response = await fetch(
          `${backendURL}/v1/gorzdrav/doctors/` +
            selectedHospitalId.toString() +
            "/" +
            selectedDirectionId.toString(),
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${auth.user?.access_token}`,
            },
          }
        );
        const data = await response.json();
        setDoctors(data);
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    };

    if (selectedDirectionId !== -1 && selectedHospitalId !== -1) {
      fetchData();
    } else {
      setDoctors([]);
    }
  }, [selectedDirectionId, selectedHospitalId, auth.user?.access_token]);

  return (
    <div className={styles.form_section}>
      <label htmlFor="doctor" className={styles.label}>
        Доктор:
      </label>
      <select name="doctor" id="doctorSelect" onChange={handleChange}>
        <option value="-1">Выберите доктора (опционально)</option>
        {Array.isArray(doctors) &&
          doctors.map((doctor) => (
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
};
