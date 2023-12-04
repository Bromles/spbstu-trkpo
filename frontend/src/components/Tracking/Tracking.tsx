import {useCallback, useEffect, useState} from "react";
import styles from "./Tracking.module.css";
import {useAuth} from "react-oidc-context";

type TrackingItemProps = {
    id: number;
    directionId: number;
    doctorId: number;
    isFinished: boolean;
    clientId: number;
    hospitalId: number;
    hospitalGorzdravId: number;
    hospitalFullName: string;
}

const backendURL =
    import.meta.env.VITE_DEV === 'true'
        ? import.meta.env.VITE_DEV_BACKEND_URL
        : import.meta.env.VITE_PROD_BACKEND_URL;

type TrackingProps = {
    reload: boolean;
}
export const Tracking = ({ reload }: TrackingProps) => {
    const [trackingItems, setTrackingItems] = useState<TrackingItemProps[]>([]);
    const [reloadData, setReloadData] = useState(false);

    const auth = useAuth();
    // const uuid = "565c59dd-f752-4f7d-bd54-c644f313bee1"; //для теста
    const uuid = auth.user?.profile.sub;

    useEffect(() => {
        const fetchData = () => {
            fetch(`${backendURL}/v1/tracking/${uuid}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${auth.user?.access_token}`
                }
            })
                .then((response) => response.json())
                .then((data: TrackingItemProps[]) => {
                    if (data !== null) {
                        setTrackingItems(data);
                    }
                });
        };

        fetchData();
    }, [reloadData, reload, auth.user?.access_token, uuid]);

    const handleReload = useCallback(() => {
        setReloadData((prevState) => !prevState);
    }, []);

    return (
        <div className={styles.tracking_container}>
            <h1>Отслеживание</h1>
            <div className={styles.tracking_container_content}>
                {trackingItems.map((item) => (
                    <TrackingItem key={item.id} item={item} onStopTracking={handleReload} />
                ))}
            </div>
        </div>
    );
};

type TrackingItemComponentProps = {
    item: TrackingItemProps;
    onStopTracking: () => void;
}

const TrackingItem: React.FC<TrackingItemComponentProps> = ({item, onStopTracking}) => {
    const [hospitalInfo, setHospitalInfo] = useState<{
        directionName: string;
        doctorName: string;
    }>();
    const auth = useAuth();

    const deleteTrackingOnClick = useCallback(() => {
        const deleteTracking = async () => {
            try {
                await fetch(`${backendURL}/v1/tracking/${item.id}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${auth.user?.access_token}`
                    }
                }).then(() => {
                    onStopTracking();
                });
            } catch (error) {
                console.error("Ошибка при получении данных:", error);
            }
        };
        deleteTracking();
    }, [item.id, onStopTracking, auth.user?.access_token]);

    useEffect(() => {
        let doctorId = item.doctorId;
        if (doctorId == null) {
            doctorId = -1;
        }
        fetch(`${backendURL}/v1/gorzdrav/trackingInfo/${item.hospitalGorzdravId}/${item.directionId}/${doctorId}`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${auth.user?.access_token}`
            }
        })
            .then((response) => response.json())
            .then((data) => setHospitalInfo(data))
            .catch((error) => console.error('Ошибка при получении данных:', error));
    }, [item.id, item.directionId, item.doctorId, item.hospitalGorzdravId, auth.user?.access_token]);

    return (
        <div className={styles.trackingItem}>
            <p>
                <h3>Больница:</h3> {item.hospitalFullName}<br />
                <h3>Направление:</h3> {hospitalInfo?.directionName}<br />
                <h3>Врач:</h3> {hospitalInfo?.doctorName}
            </p>
            <button type="button" onClick={deleteTrackingOnClick}>
                Закончить отслеживание
            </button>
        </div>
    );
};