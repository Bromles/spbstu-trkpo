import {ChangeEvent, useEffect} from "react";
import styles from "@/pages/Home/Home.module.css";

interface Direction {
    id: number;
    countFreeTicket: number;
    name: string;
}

interface DirectionSelectionProps {
    selectedHospitalId: number;
    onDirectionChange: (selectedDirectionId: number) => void;
}

export const DirectionSelection = ({ selectedHospitalId, onDirectionChange }: DirectionSelectionProps) => {

    const handleChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDirectionId = parseInt(event.target.value, 10);
        onDirectionChange(selectedDirectionId);
    };

    const fetchData = () => {
        fetch("http://localhost:8082/v1/gorzdrav/specialties/" + selectedHospitalId.toString())
            .then((response) => response.json())
            .then((data: Direction[]) => {
                const directionSelect = document.getElementById("directionSelect") as HTMLSelectElement;
                directionSelect.innerHTML = "";

                const defaultOption = document.createElement("option");
                defaultOption.value = "-1";
                defaultOption.textContent = "Выберите направление";
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
    }, [selectedHospitalId]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="direction" className={styles.label}>
                Направление:
            </label>
            <select name="direction" id="directionSelect" onChange={handleChange}></select>
        </div>
    );
};