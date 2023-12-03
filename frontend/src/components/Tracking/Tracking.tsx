import {useEffect, useState} from "react";
import styles from "./Tracking.module.css";
import {useAuth} from "react-oidc-context";

interface TrackingItemProps {
    id: number;
    directionId: number;
    doctorId: number;
    isFinished: boolean;
    clientId: number;
    hospitalId: number;
    hospitalGorzdravId: number;
    hospitalFullName: string;
}
function parseJwt(token: string) {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
}

export const Tracking = () => {
    const [trackingItems, setTrackingItems] = useState<TrackingItemProps[]>([]);
    const auth = useAuth();
    let uuid = -1;

    if (auth && auth.user && auth.user.access_token) {
        const token = auth.user.access_token;
        const parsedToken = parseJwt(token);
        uuid = parsedToken && parsedToken.sub;
    }

    const fetchData = () => {
        fetch("http://localhost:8082/v1/tracking/" + uuid)
            .then((response) => response.json())
            .then((data: TrackingItemProps[]) => {
                if (data !== null) {
                    setTrackingItems(data);
                }
            });
        };

    useEffect(() => {
        fetchData();
    });

    return (
        <div className={styles.tracking_container}>
            <h1>Отслеживание</h1>
            <div className={styles.tracking_container_content}>
                {trackingItems.map((item) => (
                    <TrackingItem key={item.id} item={item} onStopTracking={fetchData} />
                ))}
            </div>
        </div>
    );
};

interface TrackingItemComponentProps {
    item: TrackingItemProps;
    onStopTracking: () => void;
}

const TrackingItem: React.FC<TrackingItemComponentProps> = ({item, onStopTracking}) => {
    const [hospitalInfo, setHospitalInfo] = useState<{
        directionName: string;
        doctorName: string;
    }>();

    const deleteTracking = async () => {
        try {
            await fetch("http://localhost:8082/v1/tracking/" + item.id, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                }
            }).then(() => {
                onStopTracking();
            });
        } catch (error) {
            console.error("Ошибка при получении данных:", error);
        }
    };

    useEffect(() => {
        fetch("http://localhost:8082/v1/gorzdrav/trackingInfo", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                hospitalId: item.hospitalId,
                directionId: item.directionId,
                doctorId: item.doctorId
            }),
        })
            .then((response) => response.json())
            .then((data) => setHospitalInfo(data))
            .catch((error) => console.error('Ошибка при получении данных:', error));
    }, [item.id, item.directionId, item.doctorId, item.hospitalId]);

    return (
        <div className={styles.trackingItem}>
            <p>
                <h3>Больница:</h3> {item.hospitalFullName}<br />
                <h3>Направление:</h3> {hospitalInfo?.directionName}<br />
                <h3>Врач:</h3> {hospitalInfo?.doctorName}
            </p>
            <button type="button" onClick={deleteTracking}>
                Перестать отслеживать
            </button>
        </div>
    );
};