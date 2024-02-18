import { render, screen } from "@testing-library/react";
import { Mock, afterEach, beforeEach, describe, expect, vi } from "vitest";
import { NavBar } from "./Navbar";
import { useAuth } from "react-oidc-context";

vi.mock("react-oidc-context", () => ({
  useAuth: vi.fn(),
}));

describe("Navbar", () => {
  beforeEach(() => {
    const auth = {};

    (useAuth as Mock).mockReturnValue(auth);
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    render(<NavBar />);
    expect(screen.getByText(/Вход/)).toBeTruthy();
  });
});
