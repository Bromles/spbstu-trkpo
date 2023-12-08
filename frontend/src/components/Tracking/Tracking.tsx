/* eslint-disable react-hooks/exhaustive-deps */
import { useCallback, useEffect, useState } from "react";
import styles from "./Tracking.module.css";
import { getBackendUrl } from "@/utils/apiUtils";
import { useClientId, useClientToken, useGlobalStore } from "@/utils/hooks";
import { TrackingItem } from "@/utils/types";
import {
  deleteTrackingItem,
  fetchHospitalInfo,
  fetchTrackingItems,
} from "./TrackingApi";
import { observer } from "mobx-react-lite";

export const Tracking = observer(() => {
  const [trackingItems, setTrackingItems] = useState<TrackingItem[] | null>();

  // const uuid = "565c59dd-f752-4f7d-bd54-c644f313bee1"; //для теста
  const clientToken = useClientToken();
  const clientId = useClientId();
  const globalStore = useGlobalStore();
  const backendUrl = getBackendUrl();

  useEffect(() => {
    const fetchData = async () => {
      const data = await fetchTrackingItems(backendUrl, clientToken, clientId);
      setTrackingItems(data);
    };

    fetchData();
  }, [globalStore.trackingToggle, clientId, clientToken, backendUrl]);

  return (
    <div className={styles.tracking_container}>
      <h1>Отслеживание</h1>
      <div className={styles.tracking_container_content}>
        {trackingItems?.map((item) => (
          <TrackingItemComponent key={item.id} item={item} />
        ))}
      </div>
    </div>
  );
});
Tracking.displayName = "Tracking";

const TrackingItemComponent = ({ item }: { item: TrackingItem }) => {
  const [hospitalInfo, setHospitalInfo] = useState<{
    directionName: string;
    doctorName: string;
  }>();
  const clientToken = useClientToken();
  const backendUrl = getBackendUrl();
  const globalStore = useGlobalStore();

  const deleteTrackingOnClick = useCallback(() => {
    const deleteTracking = async () => {
      await deleteTrackingItem(backendUrl, clientToken, item.id);
      globalStore.toggleReload();
    };

    deleteTracking();
  }, [backendUrl, clientToken, item.id]);

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
      <span>
        <h3>Больница:</h3> {item.hospitalFullName}
        <br />
        <h3>Направление:</h3> {hospitalInfo?.directionName}
        <br />
        <h3>Врач:</h3> {hospitalInfo?.doctorName}
      </span>
      <button type="button" onClick={deleteTrackingOnClick}>
        Закончить отслеживание
      </button>
    </div>
  );
};
