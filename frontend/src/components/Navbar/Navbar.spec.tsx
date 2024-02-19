import { render, screen } from "@testing-library/react";
import { Mock, afterEach, beforeEach, describe, expect, vi } from "vitest";
import { NavBar } from "./Navbar";
import { useAuth } from "react-oidc-context";

vi.mock("react-oidc-context", () => ({
  useAuth: vi.fn(),
}));

describe("Navbar", () => {
  beforeEach(() => {});

  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    const auth = {};

    (useAuth as Mock).mockReturnValue(auth);

    render(<NavBar />);
    expect(screen.getByText(/Вход/)).toBeTruthy();
  });

  it("should render with user", () => {
    const auth = {
      isAuthenticated: true,
      user: { profile: { preferred_username: "Test user" } },
    };

    (useAuth as Mock).mockReturnValue(auth);

    render(<NavBar />);
    expect(screen.getByText(/Test user/)).toBeTruthy();
  });
});
