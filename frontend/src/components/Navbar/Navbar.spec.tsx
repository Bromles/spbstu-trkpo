import { render, screen } from "@testing-library/react";
import { Mock, afterEach, describe, expect, vi } from "vitest";
import { NavBar } from "./Navbar";
import { useAuth } from "react-oidc-context";

vi.mock("react-oidc-context", () => ({
  useAuth: vi.fn(),
}));

describe("Navbar", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    const auth = {};

    (useAuth as Mock).mockReturnValue(auth);

    render(<NavBar />);
    expect(screen.getByText(/Вход/)).toBeTruthy();
  });

  it("should render exit button on active user", () => {
    const auth = {
      isAuthenticated: true,
    };

    (useAuth as Mock).mockReturnValue(auth);

    render(<NavBar />);
    expect(screen.getByText(/Выход/)).toBeTruthy();
  });

  it("should render with user email", () => {
    const auth = {
      isAuthenticated: true,
      user: { profile: { preferred_username: "test@test.ru" } },
    };

    (useAuth as Mock).mockReturnValue(auth);

    render(<NavBar />);
    expect(screen.getByText(/test@test.ru/)).toBeTruthy();
  });

  it("should render with user lastname", () => {
    const auth = {
      isAuthenticated: true,
      user: { profile: { family_name: "Lastname" } },
    };

    (useAuth as Mock).mockReturnValue(auth);

    render(<NavBar />);
    expect(screen.getByText(/Lastname/)).toBeTruthy();
  });
});
