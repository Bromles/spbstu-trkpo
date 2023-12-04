import {ChangeEvent, useCallback, useEffect, useState} from "react";
import styles from "@/pages/Home/Home.module.css";

type District = {
    id: number;
    gorzdravId: number;
    name: string;
}

type DistrictSelectionProps = {
    onChange: (selectedDistrict: number) => void;
}
export const DistrictSelection = ({ onChange }: DistrictSelectionProps) => {
    const [districts, setDistricts] = useState<District[]>([]);

    const handleDistrictChange = useCallback((event: ChangeEvent<HTMLSelectElement>) => {
        const selectedDistrict = parseInt(event.target.value, 10);
        onChange(selectedDistrict);
    }, [onChange]);

    useEffect(() => {
        const backendURL =
            import.meta.env.VITE_DEV === 'true'
                ? import.meta.env.VITE_DEV_BACKEND_URL
                : import.meta.env.VITE_PROD_BACKEND_URL;

        const fetchData = async () => {
            try {
                const response = await fetch(`${backendURL}/v1/gorzdrav/district`);
                const data: District[] = await response.json();
                setDistricts(data);
            } catch (error) {
                console.error('Ошибка при получении данных:', error);
            }
        };

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
                {districts.map((district) => (
                    <option value={district.gorzdravId.toString()} key={district.gorzdravId}>
                        {district.name}
                    </option>
                ))}
            </select>
        </div>
    );
};