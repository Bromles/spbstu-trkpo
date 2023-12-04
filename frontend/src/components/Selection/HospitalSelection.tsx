import {ChangeEvent, useCallback, useEffect, useState} from "react";
import styles from "@/pages/Home/Home.module.css";

type Hospital = {
    id: number;
    gorzdravId: number;
    latitude: number;
    longitude: number;
    districtId: number;
    address: string;
    fullName: string;
    shortName: string;
    phone: string;
}

type HospitalSelectionProps = {
    selectedDistrictId: number;
    onHospitalChange: (selectedHospitalId: number) => void;
}

export const HospitalSelection = ({selectedDistrictId, onHospitalChange}: HospitalSelectionProps) => {

    const [hospitals, setHospitals] = useState([]);

    const handleChange = useCallback((event: ChangeEvent<HTMLSelectElement>) => {
        const selectedHospitalId = parseInt(event.target.value, 10);
        onHospitalChange(selectedHospitalId);
    }, [onHospitalChange]);

    useEffect(() => {
        const backendURL =
            import.meta.env.VITE_DEV === 'true'
                ? import.meta.env.VITE_DEV_BACKEND_URL
                : import.meta.env.VITE_PROD_BACKEND_URL;
        const fetchData = async () => {
            try {
                const response = await fetch(`${backendURL}/v1/gorzdrav/hospital`);
                const data = await response.json();

                const filteredHospitals = data.filter((hospital: Hospital) => selectedDistrictId === hospital.districtId);
                setHospitals(filteredHospitals);
            } catch (error) {
                console.error("Ошибка при получении данных:", error);
            }
        };

        fetchData();
    }, [selectedDistrictId]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="hospital" className={styles.label}>
                Больница:
            </label>
            <select name="hospital" id="hospitalSelect" onChange={handleChange}>
                <option value="-1">Выберите учреждение</option>
                {hospitals.map((hospital: Hospital) => (
                    <option key={hospital.gorzdravId} value={hospital.gorzdravId}>
                        {hospital.shortName}
                    </option>
                ))}
            </select>
        </div>
    );
};