import {AuthContextProps} from "react-oidc-context";
import {
    QuerySessionStatusArgs,
    RevokeTokensTypes, SessionStatus,
    SigninPopupArgs,
    SigninRedirectArgs,
    SigninSilentArgs,
    SignoutPopupArgs,
    SignoutRedirectArgs,
    SignoutSilentArgs,
    User,
    UserManagerEvents,
    UserManagerSettings
} from "oidc-client-ts";
import {makeObservable} from "mobx";
import { UserManager } from 'oidc-client-ts';

export class MockAuthContextProps implements AuthContextProps {
    readonly events: UserManagerEvents;
    isAuthenticated: boolean;
    isLoading: boolean;
    readonly settings: UserManagerSettings;
    activeNavigator?: "signinRedirect" | "signinPopup" | "signinSilent" | "signoutRedirect" | "signoutPopup" | "signoutSilent";
    error?: Error;


    constructor(isLoading: boolean, isAuthenticated: boolean,
                activeNavigator: "signinRedirect" | "signinPopup" | "signinSilent" | "signoutRedirect" | "signoutPopup" | "signoutSilent",
                error?: Error) {
        const config = {
            authority: 'https://your-identity-provider-url',
            client_id: 'your-client-id',
            redirect_uri: 'https://your-app-url/callback',
            response_type: 'code',
            scope: 'openid profile email',
        };

        const userManager = new UserManager(config);
        this.isLoading = isLoading;
        this.isAuthenticated = isAuthenticated;
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        this.events = userManager.events; // new UserManagerEvents(new UserManagerSettingsStore(new MockUserManagerSettings()));
        this.settings = new MockUserManagerSettings();
        this.error = error;
        this.activeNavigator = activeNavigator;
        makeObservable(this, {
            isAuthenticated: true,
            isLoading: true
        });
    }
    clearStaleState(): Promise<void> {
        return Promise.resolve();
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    querySessionStatus(_args?: QuerySessionStatusArgs): Promise<SessionStatus | null> {
        // Acknowledge arguments without using them.
        return Promise.resolve(null);
    }

    removeUser(): Promise<void> {
        return Promise.resolve(undefined);
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    revokeTokens(_types?: RevokeTokensTypes): Promise<void> {
        return Promise.resolve(undefined);
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    signinPopup(_args?: SigninPopupArgs): Promise<User> {
        const mockUser: User = {
            access_token: "",
            get expires_in(): number | undefined {
                return undefined;
            },
            profile: {
                name: "Mock User",
                email: "mockuser@example.com",
                preferred_username: "mockuser",
                sub: "",
                iss: "",
                aud: "",
                exp: 12,
                iat: 34,
            },
            session_state: null,
            state: undefined,
            token_type: "",
            get expired(): boolean | undefined {
                return undefined;
            },
            get scopes(): string[] {
                return [];
            },
            toStorageString(): string {
                return "";
            }
        };
        return Promise.resolve(mockUser);
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    signinRedirect(_args?: SigninRedirectArgs): Promise<void> {
        return Promise.resolve(undefined);
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    signinSilent(_args?: SigninSilentArgs): Promise<User | null> {
        return Promise.resolve(null);
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    signoutPopup(_args?: SignoutPopupArgs): Promise<void> {
        return Promise.resolve(undefined);
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    signoutRedirect(_args?: SignoutRedirectArgs): Promise<void> {
        return Promise.resolve();
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    signoutSilent(_args?: SignoutSilentArgs): Promise<void> {
        return Promise.resolve();
    }


    startSilentRenew(): void {
    }

    stopSilentRenew(): void {
    }
}

export class MockUserManagerSettings implements UserManagerSettings {
    authority: string;
    client_id: string;
    redirect_uri: string;

    constructor() {
        this.authority = "kate";
        this.client_id = "";
        this.redirect_uri = "";
    }
}

