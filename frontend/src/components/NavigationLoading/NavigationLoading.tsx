import { useNavigation } from "react-router-dom";

export const NavigationLoading = ({children = <></>}: {children?: React.ReactNode}) => {
    const navigation = useNavigation();

    if (navigation.state !== "idle") {
      return <LoaderBody />;
    } else {
      return children;
    }
};

const LoaderBody = () => {
    return (
      <div>
        <p>Navigation in progress...</p>
      </div>
    );
};