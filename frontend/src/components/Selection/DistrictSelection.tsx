import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import {
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { observer } from "mobx-react-lite";
import { fetchDistricts } from "./SelectionApi";

export const DistrictSelection = observer(() => {
  const clientToken = useClientToken();
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const handleDistrictChange = useCallback(
    (e: ChangeEvent<HTMLSelectElement>) => {
      const selectedDistrict = parseInt(e.target.value, 10);
      selectionStore.selectedDistrictId = selectedDistrict;
    },
    [selectionStore]
  );

  useEffect(() => {
    const backendUrl = getBackendUrl();

    const fetchData = async () => {
      globalStore.districts = await fetchDistricts(backendUrl, clientToken);
    };

    fetchData();
  }, [clientToken, globalStore]);

  return (
    <div className={styles.form_section}>
      <label htmlFor="district" className={styles.label}>
        Район:
      </label>
      <select
        name="district"
        id="districtSelect"
        onChange={handleDistrictChange}
        defaultValue="-1"
      >
        <option value="-1">Выберите район</option>
        {globalStore.districts.map((district) => (
          <option
            value={district.gorzdravId.toString()}
            key={district.gorzdravId}
          >
            {district.name}
          </option>
        ))}
      </select>
    </div>
  );
});
