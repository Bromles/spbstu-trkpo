import { ChangeEvent, useCallback, useEffect } from "react";
import styles from "@/pages/Home/Home.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { fetchDirections } from "./SelectionApi";
import { useClientToken } from "@/utils/hooks";
import { GlobalStore } from "@/GlobalStore";
import { observer } from "mobx-react-lite";

type DirectionSelectionProps = {
  selectedHospitalId: number;
  onDirectionChange: (selectedDirectionId: number) => void;
};

export const DirectionSelection = observer(
  ({ selectedHospitalId, onDirectionChange }: DirectionSelectionProps) => {
    const clientToken = useClientToken();

    const handleDirectionChange = useCallback(
      (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDirectionId = parseInt(event.target.value, 10);
        onDirectionChange(selectedDirectionId);
      },
      [onDirectionChange]
    );

    useEffect(() => {
      const backendUrl = getBackendUrl();
      const fetchData = async () => {
        GlobalStore.directions = await fetchDirections(
          backendUrl,
          clientToken,
          selectedHospitalId
        );
      };

      if (selectedHospitalId !== -1) {
        fetchData();
      } else {
        GlobalStore.directions = [];
      }
    }, [selectedHospitalId, clientToken]);

    return (
      <div className={styles.form_section}>
        <label htmlFor="direction" className={styles.label}>
          Направление:
        </label>
        <select
          name="direction"
          id="directionSelect"
          onChange={handleDirectionChange}
        >
          <option value="-1">Выберите направление</option>
          {GlobalStore.directions.map((direction) => (
            <option value={direction.id.toString()} key={direction.id}>
              {direction.name}
            </option>
          ))}
        </select>
      </div>
    );
  }
);
