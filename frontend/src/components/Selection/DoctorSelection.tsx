import {ChangeEvent, useEffect} from "react";
import styles from "@/pages/Home/Home.module.css";

interface Doctor {
    id: number;
    countFreeTicket: number;
    name: string;
}

interface DoctorSelectionProps {
    selectedDirectionId: number;
    onDoctorChange: (selectedDoctorId: number) => void;
}

export const DoctorSelection = ({ selectedDirectionId, onDoctorChange }: DoctorSelectionProps) => {

    const handleChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDoctorId = parseInt(event.target.value, 10);
        onDoctorChange(selectedDoctorId);
    };

    const fetchData = () => {
        fetch("http://localhost:8082/v1/gorzdrav/doctor/" + selectedDirectionId.toString())
            .then((response) => response.json())
            .then((data: Doctor[]) => {
                const directionSelect = document.getElementById("doctorSelect") as HTMLSelectElement;
                directionSelect.innerHTML = "";

                const defaultOption = document.createElement("option");
                defaultOption.value = "-1";
                defaultOption.textContent = "Выберите доктора (опционально)";
                directionSelect.appendChild(defaultOption);

                data.forEach((direction) => {
                    const option = document.createElement("option");
                    option.value = direction.id.toString();
                    option.textContent = direction.name;
                    directionSelect.appendChild(option);
                });
            })
            .catch((error) => {
                console.error("Ошибка при получении данных:", error);
            });
    };

    useEffect(() => {
        fetchData();
    }, [selectedDirectionId]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="doctor" className={styles.label}>
                Доктор:
            </label>
            <select name="doctor" id="doctorSelect" onChange={handleChange}></select>
        </div>
    );
};