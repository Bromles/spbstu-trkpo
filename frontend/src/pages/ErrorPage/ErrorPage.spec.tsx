import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { ErrorPage } from "./ErrorPage";
import { useRouteError } from "react-router-dom";
import { Mock, afterEach } from "vitest";

vi.mock("react-router-dom", () => ({
  useRouteError: vi.fn(),
  Link: vi.fn(),
}));

describe("ErrorPage", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("renders and shows the correct error message", () => {
    const error = {};

    (useRouteError as Mock).mockReturnValue(error);

    render(<ErrorPage />);
    expect(screen.getByText("Something went wrong")).toBeInTheDocument();
  });
});
