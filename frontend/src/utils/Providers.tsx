import i18n from "@/utils/i18n.ts";
import {createContext} from "react";

const I18nContext = createContext<typeof i18n>({} as typeof i18n);

export const Providers = ({children}: { children: React.ReactNode }) => {
    return (
        <>
            <I18nContext.Provider value={i18n}>
                {children}
            </I18nContext.Provider>
        </>
    );
};