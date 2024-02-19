import {render, screen} from '@testing-library/react';
import {describe, expect, Mock} from 'vitest';
import {AuthGuard} from '@/components/AuthGuard/AuthGuard';
import {MockAuthContextProps} from "@/mocks/authContextProps";
import {useAuth} from "react-oidc-context";

vi.mock('react-oidc-context', () => ({
    useAuth: vi.fn(),
}));

describe('AuthGuard', () => {

    it('shows loading if auth is loading', () => {
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(true, false, "signinRedirect", undefined));
        render(<AuthGuard>any</AuthGuard>);
        const loadingAuth = screen.getByText("Auth is loading...");
        expect(loadingAuth).toBeTruthy();
    });

    it('displays error if auth has error', () => {
        const error = new Error('Test Error');
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(false, false, "signoutSilent", error));
        render(<AuthGuard>any</AuthGuard>);
        const errorAuth = screen.getByText(`Oops... Auth error ${error.message}`);
        expect(errorAuth).toBeTruthy();
    });

    it('renders UnauthHome component if user is not authenticated', () => {
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(false, false, "signoutSilent", undefined));
        render(<AuthGuard>any</AuthGuard>);
        const unauth = screen.getByText('Описание сервиса');
        expect(unauth).toBeTruthy();
    });

    it('renders children if user is authenticated', () => {
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(false, true, "signinPopup", undefined));
        const childText = 'Child component';
        render(<AuthGuard>{childText}</AuthGuard>);
        expect(screen.getByText(childText)).toBeTruthy();
    });
});
