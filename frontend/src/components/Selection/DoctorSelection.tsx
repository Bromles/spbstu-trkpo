import {ChangeEvent, useCallback, useEffect, useState} from "react";
import styles from "@/pages/Home/Home.module.css";

type Doctor = {
    gorzdravId: number;
    name: string;
}

type DoctorSelectionProps = {
    selectedDirectionId: number;
    selectedHospitalId: number;
    onDoctorChange: (selectedDoctorId: number) => void;
}

export const DoctorSelection = ({ selectedDirectionId, selectedHospitalId, onDoctorChange }: DoctorSelectionProps) => {
    const [doctors, setDoctors] = useState<Doctor[]>([]);

    const handleChange = useCallback((event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDoctorId = parseInt(event.target.value, 10);
        onDoctorChange(selectedDoctorId);
    }, [onDoctorChange]);

    useEffect(() => {
        const backendURL =
            import.meta.env.VITE_DEV === 'true'
                ? import.meta.env.VITE_DEV_BACKEND_URL
                : import.meta.env.VITE_PROD_BACKEND_URL;

        const fetchData = async () => {
            try {
                const response = await fetch(`${backendURL}/v1/gorzdrav/doctors/` + selectedHospitalId.toString() + "/" + selectedDirectionId.toString());
                const data = await response.json();
                setDoctors(data);
            } catch (error) {
                console.error("Ошибка при получении данных:", error);
            }
        };

        if (selectedDirectionId !== -1 && selectedHospitalId !== -1 ) {
            fetchData();
        } else {
            setDoctors([]);
        }
    }, [selectedDirectionId, selectedHospitalId]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="doctor" className={styles.label}>
                Доктор:
            </label>
            <select name="doctor" id="doctorSelect" onChange={handleChange}>
                <option value="-1">Выберите доктора (опционально)</option>
                {Array.isArray(doctors) && doctors.map((doctor) => (
                    <option value={doctor.gorzdravId.toString()} key={doctor.gorzdravId}>
                        {doctor.name}
                    </option>
                ))}
            </select>
        </div>
    );
};