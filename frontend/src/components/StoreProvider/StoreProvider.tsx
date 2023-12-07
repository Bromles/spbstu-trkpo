import { GlobalStore } from "@/stores/GlobalStore";
import React, { useState } from "react";
import { SelectionStore } from "../../stores/SelectionStore";

export const GlobalStoreContext = React.createContext<GlobalStore | null>(null);
export const SelectionStoreContext = React.createContext<SelectionStore | null>(
  null
);

export const StoreProvider = ({ children }: { children: React.ReactNode }) => {
  const [globalStore] = useState(() => new GlobalStore());
  const [selectionStore] = useState(() => new SelectionStore());

  return (
    <GlobalStoreContext.Provider value={globalStore}>
      <SelectionStoreContext.Provider value={selectionStore}>
        {children}
      </SelectionStoreContext.Provider>
    </GlobalStoreContext.Provider>
  );
};
