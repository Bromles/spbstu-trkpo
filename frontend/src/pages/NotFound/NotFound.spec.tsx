import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { NotFound } from "./NotFound";
import { afterEach } from "vitest";

vi.mock("react-router-dom", () => ({
  Link: vi.fn(),
}));

describe("NotFound", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("renders and shows the correct error message", () => {
    render(<NotFound />);
    expect(screen.getByText("Не найдено")).toBeInTheDocument();
  });
});
