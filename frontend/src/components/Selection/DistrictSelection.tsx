/* eslint-disable react-hooks/exhaustive-deps */
import { ChangeEvent, useCallback } from "react";
import styles from "@/pages/Home/Home.module.css";
import { useGlobalStore, useSelectionStore } from "@/utils/hooks";
import { observer } from "mobx-react-lite";

export const DistrictSelection = observer(() => {
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const handleDistrictChange = useCallback(
    (e: ChangeEvent<HTMLSelectElement>) => {
      const selectedDistrict = parseInt(e.target.value, 10);
      selectionStore.selectedDistrictId = selectedDistrict;
      selectionStore.selectedHospitalId = -1;
      selectionStore.selectedDirectionId = -1;
      selectionStore.selectedDoctorId = -1;
    },
    []
  );

  return (
    <div className={styles.form_section}>
      <label htmlFor="districtSelect" className={styles.label}>
        Район:
      </label>
      <select
        name="district"
        id="districtSelect"
        onChange={handleDistrictChange}
        value={selectionStore.selectedDistrictId}
      >
        <option value="-1">Выберите район</option>
        {globalStore.districts.map((district) => (
          <option value={district.gorzdravId} key={district.gorzdravId}>
            {district.name}
          </option>
        ))}
      </select>
    </div>
  );
});
DistrictSelection.displayName = "DistrictSelection";
