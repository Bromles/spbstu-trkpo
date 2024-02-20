import { render } from "@testing-library/react";
import { NavigationLoading } from "./NavigationLoading";
import { useNavigation } from "react-router-dom";
import { Mock } from "vitest";

vi.mock("react-router-dom", () => ({
  useNavigation: vi.fn(),
  Outlet: vi.fn(),
}));

describe("NavigationLoading", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });
  
  it("should render", () => {
    const navigation = {};

    (useNavigation as Mock).mockReturnValue(navigation);

    render(<NavigationLoading />);
  });
});
