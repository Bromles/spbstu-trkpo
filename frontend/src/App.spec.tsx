import { render } from "@testing-library/react";
import { Mock, afterEach, describe, vi } from "vitest";
import { App } from "./App";
import { useAuth } from "react-oidc-context";
import { useNavigation } from "react-router-dom";

vi.mock("react-oidc-context", () => ({
  useAuth: vi.fn(),
}));

vi.mock("react-router-dom", () => ({
  useNavigation: vi.fn(),
  Outlet: vi.fn(),
}));

describe("App", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    const auth = {};
    const navigation = {};

    (useNavigation as Mock).mockReturnValue(navigation);
    (useAuth as Mock).mockReturnValue(auth);

    render(<App />);
  });
});
