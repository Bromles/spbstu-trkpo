import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { useClientToken } from "@/utils/hooks";
import { observer } from "mobx-react-lite";
import { GlobalStore } from "@/GlobalStore";
import { fetchDistricts } from "./SelectionApi";

type DistrictSelectionProps = {
  onChange: (selectedDistrict: number) => void;
};

export const DistrictSelection = observer(
  ({ onChange }: DistrictSelectionProps) => {
    const clientToken = useClientToken();

    const handleDistrictChange = useCallback(
      (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDistrict = parseInt(event.target.value, 10);
        onChange(selectedDistrict);
      },
      [onChange]
    );

    useEffect(() => {
      const backendUrl = getBackendUrl();

      const fetchData = async () => {
        GlobalStore.districts = await fetchDistricts(backendUrl, clientToken);
      };

      fetchData();
    }, [clientToken]);

    return (
      <div className={styles.form_section}>
        <label htmlFor="district" className={styles.label}>
          Район:
        </label>
        <select
          name="district"
          id="districtSelect"
          onChange={handleDistrictChange}
        >
          <option defaultValue="-1">Выберите район</option>
          {GlobalStore.districts.map((district) => (
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
  }
);
