import {ChangeEvent, useEffect} from "react";
import styles from "@/pages/Home/Home.module.css";

interface District {
    id: number;
    gorzdravId: number;
    name: string;
}

interface DistrictSelectionProps {
    onChange: (selectedDistrict: number) => void;
}
export const DistrictSelection = ({ onChange }: DistrictSelectionProps) => {
    const handleDistrictChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDistrict = parseInt(event.target.value, 10);
        onChange(selectedDistrict);
    };
    const fetchData = () => {
        fetch('http://localhost:8082/v1/gorzdrav/district')
            .then((response) => response.json())
            .then((data: District[]) => {
                const districtSelect = document.getElementById('districtSelect') as HTMLSelectElement;
                data.forEach((district) => {
                    const option = document.createElement('option');
                    option.value = district.gorzdravId.toString();
                    option.textContent = district.name;
                    districtSelect.appendChild(option);
                });
            })
            .catch((error) => {
                console.error('Ошибка при получении данных:', error);
            });
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <div className={styles.form_section}>
            <label htmlFor="district" className={styles.label}>
                Район:
            </label>
            <select name="district" id="districtSelect" onChange={handleDistrictChange}>
                <option selected value="-1">
                    Выберите район
                </option>
            </select>
        </div>
    );
};