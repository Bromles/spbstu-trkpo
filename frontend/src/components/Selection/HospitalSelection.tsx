import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { useAuth } from "react-oidc-context";
import { getBackendUrl } from "@/utils/apiUtils";
import { Hospital } from "@/utils/types";

type HospitalSelectionProps = {
  selectedDistrictId: number;
  onHospitalChange: (selectedHospitalId: number) => void;
};

export const HospitalSelection = ({
  selectedDistrictId,
  onHospitalChange,
}: HospitalSelectionProps) => {
  const { hospitals, setHospitals } = useHospitalsContext();
  const auth = useAuth();

  const handleChange = useCallback(
    (event: ChangeEvent<HTMLSelectElement>) => {
      const selectedHospitalId = parseInt(event.target.value, 10);
      onHospitalChange(selectedHospitalId);
    },
    [onHospitalChange]
  );

  useEffect(() => {
    const backendURL = getBackendUrl();
    const fetchData = async () => {
      try {
        const response = await fetch(`${backendURL}/v1/gorzdrav/hospital`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${auth.user?.access_token}`,
          },
        });
        const data = await response.json();

        const filteredHospitals = data.filter(
          (hospital: Hospital) => selectedDistrictId === hospital.districtId
        );
        setHospitals(filteredHospitals);
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    };

    fetchData();
  }, [selectedDistrictId, auth.user?.access_token, setHospitals]);

  return (
    <div className={styles.form_section}>
      <label htmlFor="hospital" className={styles.label}>
        Больница:
      </label>
      <select name="hospital" id="hospitalSelect" onChange={handleChange}>
        <option value="-1">Выберите учреждение</option>
        {hospitals?.map((hospital: Hospital) => (
          <option key={hospital.gorzdravId} value={hospital.gorzdravId}>
            {hospital.shortName}
          </option>
        ))}
      </select>
    </div>
  );
};
