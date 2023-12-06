import { useCallback, useEffect, useState } from "react";
import styles from "./Tracking.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { useClientId, useClientToken } from "@/utils/hooks";
import { TrackingItem } from "@/utils/types";
import { GlobalStore } from "@/GlobalStore";
import {
  deleteTrackingItem,
  fetchHospitalInfo,
  fetchTrackingItems,
} from "./TrackingApi";
import { observer } from "mobx-react-lite";

type TrackingProps = {
  reload: boolean;
};
export const Tracking = observer(({ reload }: TrackingProps) => {
  const [reloadData, setReloadData] = useState(false);

  // const uuid = "565c59dd-f752-4f7d-bd54-c644f313bee1"; //для теста
  const clientToken = useClientToken();
  const clientId = useClientId();
  const backendUrl = getBackendUrl();

  useEffect(() => {
    const fetchData = async () => {
      GlobalStore.trackingItems = await fetchTrackingItems(
        backendUrl,
        clientToken,
        clientId
      );
    };

    fetchData();
  }, [reloadData, reload, clientId, clientToken, backendUrl]);

  const handleReload = useCallback(() => {
    setReloadData((prevState) => !prevState);
  }, []);

  return (
    <div className={styles.tracking_container}>
      <h1>Отслеживание</h1>
      <div className={styles.tracking_container_content}>
        {GlobalStore.trackingItems.map((item) => (
          <TrackingItem
            key={item.id}
            item={item}
            onStopTracking={handleReload}
          />
        ))}
      </div>
    </div>
  );
});

type TrackingItemComponentProps = {
  item: TrackingItem;
  onStopTracking: () => void;
};

const TrackingItem: React.FC<TrackingItemComponentProps> = ({
  item,
  onStopTracking,
}) => {
  const [hospitalInfo, setHospitalInfo] = useState<{
    directionName: string;
    doctorName: string;
  }>();
  const clientToken = useClientToken();
  const backendUrl = getBackendUrl();

  const deleteTrackingOnClick = useCallback(() => {
    const deleteTracking = async () => {
      await deleteTrackingItem(backendUrl, clientToken, item.id);
      onStopTracking();
    };

    deleteTracking();
  }, [backendUrl, clientToken, item.id, onStopTracking]);

  useEffect(() => {
    let doctorId = item.doctorId;
    if (doctorId == null) {
      doctorId = -1;
    }

    const fetchData = async () => {
      const res = await fetchHospitalInfo(
        backendUrl,
        clientToken,
        item.hospitalGorzdravId,
        item.directionId,
        doctorId
      );
      setHospitalInfo(res);
    };

    fetchData();
  }, [
    item.id,
    item.directionId,
    item.doctorId,
    item.hospitalGorzdravId,
    clientToken,
    backendUrl,
  ]);

  return (
    <div className={styles.trackingItem}>
      <p>
        <h3>Больница:</h3> {item.hospitalFullName}
        <br />
        <h3>Направление:</h3> {hospitalInfo?.directionName}
        <br />
        <h3>Врач:</h3> {hospitalInfo?.doctorName}
      </p>
      <button type="button" onClick={deleteTrackingOnClick}>
        Закончить отслеживание
      </button>
    </div>
  );
};
