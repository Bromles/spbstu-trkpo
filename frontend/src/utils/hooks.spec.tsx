import {expect, Mock} from "vitest";
import {useAuth} from "react-oidc-context";
import {MockAuthContextProps} from "@/mocks/authContextProps";
import {useClientEmail, useClientId, useClientToken} from "@/utils/hooks";

vi.mock('react-oidc-context', () => ({
    useAuth: vi.fn(),
}));

describe('hooks', () => {

    it("useClientToken", () => {
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(true, false, "signinRedirect", undefined));
        expect(useClientToken()).toEqual("mock-access-token");
    });

    it("useClientId", () => {
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(true, false, "signinRedirect", undefined));
        expect(useClientId()).toEqual("mock-sub");
    });

    it("useClientEmail", () => {
        (useAuth as Mock).mockReturnValue(new MockAuthContextProps(true, false, "signinRedirect", undefined));
        expect(useClientEmail()).toEqual("mockuser");
    });
});