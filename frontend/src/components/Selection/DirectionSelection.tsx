/* eslint-disable react-hooks/exhaustive-deps */
import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { fetchDirections } from "@/components/Selection/SelectionApi";
import {
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { observer } from "mobx-react-lite";
import { autorun } from "mobx";

export const DirectionSelection = observer(() => {
  const clientToken = useClientToken();
  const globalStore = useGlobalStore();
  const selectionStore = useSelectionStore();

  const handleDirectionChange = useCallback(
    (e: ChangeEvent<HTMLSelectElement>) => {
      const selectedDirectionId = parseInt(e.target.value, 10);
      selectionStore.selectedDirectionId = selectedDirectionId;
      selectionStore.selectedDoctorId = -1;
    },
    []
  );

  useEffect(
    () =>
      autorun(() => {
        const backendUrl = getBackendUrl();
        const fetchData = async () => {
          globalStore.directions = await fetchDirections(
            backendUrl,
            clientToken,
            selectionStore.selectedHospitalId
          );
        };

        if (
          selectionStore.selectedHospitalId !== -1 &&
          selectionStore.selectedDistrictId !== -1
        ) {
          fetchData();
        } else {
          globalStore.directions = [];
          selectionStore.selectedDirectionId = -1;
        }
      }),
    [clientToken]
  );

  return (
    <div className={styles.form_section}>
      <label htmlFor="directionSelect" className={styles.label}>
        Направление:
      </label>
      <select
        name="direction"
        id="directionSelect"
        onChange={handleDirectionChange}
        value={selectionStore.selectedDirectionId}
        disabled={selectionStore.selectedHospitalId === -1}
      >
        <option value="-1">Выберите направление</option>
        {globalStore.directions.map((direction) => (
          <option value={direction.id} key={direction.id}>
            {direction.name}
          </option>
        ))}
      </select>
    </div>
  );
});
DirectionSelection.displayName = "DirectionSelection";
