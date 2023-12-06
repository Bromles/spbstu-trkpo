import { ChangeEvent, useCallback, useEffect, useState } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { Hospital } from "@/utils/types";
import { useClientToken } from "@/utils/hooks";
import { fetchHospitals } from "@/pages/Home/HomeApi";
import { observer } from "mobx-react-lite";

type HospitalSelectionProps = {
  selectedDistrictId: number;
  onHospitalChange: (selectedHospitalId: number) => void;
};

export const HospitalSelection = observer(
  ({ selectedDistrictId, onHospitalChange }: HospitalSelectionProps) => {
    const [filteredHospitals, setFilteredHospitals] = useState<Hospital[]>([]);
    const clientToken = useClientToken();

    const handleChange = useCallback(
      (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedHospitalId = parseInt(event.target.value, 10);
        onHospitalChange(selectedHospitalId);
      },
      [onHospitalChange]
    );

    useEffect(() => {
      const backendUrl = getBackendUrl();
      const fetchData = async () => {
        const data = await fetchHospitals(backendUrl, clientToken);
        const filtered = data.filter(
          (hospital: Hospital) => selectedDistrictId === hospital.districtId
        );
        setFilteredHospitals(filtered);
      };

      fetchData();
    }, [selectedDistrictId, clientToken]);

    return (
      <div className={styles.form_section}>
        <label htmlFor="hospital" className={styles.label}>
          Больница:
        </label>
        <select name="hospital" id="hospitalSelect" onChange={handleChange}>
          <option value="-1">Выберите учреждение</option>
          {filteredHospitals.map((hospital: Hospital) => (
            <option key={hospital.gorzdravId} value={hospital.gorzdravId}>
              {hospital.shortName}
            </option>
          ))}
        </select>
      </div>
    );
  }
);
