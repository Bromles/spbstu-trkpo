/* eslint-disable react-hooks/exhaustive-deps */
import { ChangeEvent, useCallback, useEffect, useState } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { Hospital } from "@/utils/types";
import { useClientToken, useSelectionStore } from "@/utils/hooks";
import { fetchHospitals } from "@/pages/Home/HomeApi";
import { observer } from "mobx-react-lite";

export const HospitalSelection = observer(() => {
  const [filteredHospitals, setFilteredHospitals] = useState<Hospital[]>([]);
  const clientToken = useClientToken();
  const selectionStore = useSelectionStore();

  const handleChange = useCallback(
    (e: ChangeEvent<HTMLSelectElement>) => {
      const selectedHospitalId = parseInt(e.target.value, 10);
      selectionStore.selectedHospitalId = selectedHospitalId;
    },
    [selectionStore.selectedHospitalId]
  );

  useEffect(() => {
    const backendUrl = getBackendUrl();

    const fetchData = async () => {
      const data = await fetchHospitals(backendUrl, clientToken);
      console.log(`hospitalId mobx: ${selectionStore.selectedHospitalId}`);
      const filtered = data.filter(
        (hospital: Hospital) =>
          selectionStore.selectedDistrictId === hospital.districtId
      );
      setFilteredHospitals(filtered);
    };

    fetchData();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [clientToken, selectionStore.selectedDistrictId, selectionStore.selectedHospitalId]);

  return (
    <div className={styles.form_section}>
      <label htmlFor="hospital" className={styles.label}>
        Больница:
      </label>
      <select
        name="hospital"
        id="hospitalSelect"
        onChange={handleChange}
        defaultValue="-1"
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
