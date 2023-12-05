import {ChangeEvent, useCallback, useEffect, useState} from "react";
import styles from "@/pages/Home/Home.module.css";
import {useAuth} from "react-oidc-context";

export type Direction = {
    id: number;
    countFreeTicket: number;
    name: string;
}

type DirectionSelectionProps = {
    selectedHospitalId: number;
    onDirectionChange: (selectedDirectionId: number) => void;
}

export const DirectionSelection = ({ selectedHospitalId, onDirectionChange }: DirectionSelectionProps) => {
    const [directions, setDirections] = useState<Direction[]>([]);
    const auth = useAuth();

    const handleDirectionChange = useCallback((event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDirectionId = parseInt(event.target.value, 10);
        onDirectionChange(selectedDirectionId);
    }, [onDirectionChange]);

    useEffect(() => {
        const backendURL =
            import.meta.env.VITE_DEV === 'true'
                ? import.meta.env.VITE_DEV_BACKEND_URL
                : import.meta.env.VITE_PROD_BACKEND_URL;
        const fetchData = async () => {
            try {
                const response = await fetch(`${backendURL}/v1/gorzdrav/specialties/`
                    + selectedHospitalId.toString(), {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${auth.user?.access_token}`
                    }
                });
                const data: Direction[] = await response.json();
                setDirections(data);
            } catch (error) {
                console.error("Ошибка при получении данных:", error);
            }
        };

        if (selectedHospitalId !== -1) {
            fetchData();
        } else {
            setDirections([]);
        }
    }, [selectedHospitalId, auth.user?.access_token]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="direction" className={styles.label}>
                Направление:
            </label>
            <select name="direction" id="directionSelect" onChange={handleDirectionChange}>
                <option value="-1">Выберите направление</option>
                {Array.isArray(directions) &&
                    directions.map((direction) => (
                        <option value={direction.id.toString()} key={direction.id}>
                            {direction.name}
                        </option>
                    ))}
            </select>
        </div>
    );
};