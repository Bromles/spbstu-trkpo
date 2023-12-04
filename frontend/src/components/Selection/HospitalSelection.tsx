import {ChangeEvent, useEffect} from "react";
import styles from "@/pages/Home/Home.module.css";

interface Hospital {
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

interface HospitalSelectionProps {
    selectedDistrictId: number;
    onHospitalChange: (selectedHospitalId: number) => void;
}

export const HospitalSelection = ({selectedDistrictId, onHospitalChange}: HospitalSelectionProps) => {

    const handleChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedHospitalId = parseInt(event.target.value, 10);
        onHospitalChange(selectedHospitalId);
    };

    const fetchData = () => {
        fetch("http://localhost:8082/v1/gorzdrav/hospital")
            .then((response) => response.json())
            .then((data: Hospital[]) => {
                const hospitalSelect = document.getElementById("hospitalSelect") as HTMLSelectElement;
                hospitalSelect.innerHTML = "";

                const defaultOption = document.createElement("option");
                defaultOption.value = "-1";
                defaultOption.textContent = "Выберите учреждение";
                hospitalSelect.appendChild(defaultOption);

                data.forEach((hospital) => {
                    if (selectedDistrictId === hospital.districtId) {
                        const option = document.createElement("option");
                        option.value = hospital.gorzdravId.toString();
                        option.textContent = hospital.shortName;
                        hospitalSelect.appendChild(option);
                    }
                });
            })
            .catch((error) => {
                console.error("Ошибка при получении данных:", error);
            });
    };

    useEffect(() => {
        fetchData();
    }, [selectedDistrictId]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="hospital" className={styles.label}>
                Больница:
            </label>
            <select name="hospital" id="hospitalSelect" onChange={handleChange}></select>
        </div>
    );
};