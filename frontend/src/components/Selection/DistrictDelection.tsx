import {ChangeEvent, useCallback, useEffect, useState} from "react";
import styles from "@/pages/Home/Home.module.css";
import {useAuth} from "react-oidc-context";

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
    const auth = useAuth();

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
                const response = await fetch(`${backendURL}/v1/gorzdrav/district`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${auth.user?.access_token}`
                    }
                });
                const data: District[] = await response.json();
                setDistricts(data);
            } catch (error) {
                console.error('Ошибка при получении данных:', error);
            }
        };

        fetchData();
    }, [auth.user?.access_token]);

    return (
        <div className={styles.form_section}>
            <label htmlFor="district" className={styles.label}>
                Район:
            </label>
            <select name="district" id="districtSelect" onChange={handleDistrictChange}>
                <option defaultValue="-1">
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