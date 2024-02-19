import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { AppLoading } from './AppLoading';

describe('AppLoading Component', () => {
    it('renders and shows the correct loading message', () => {
        render(<AppLoading />);
        expect(screen.getByText("App Loading...")).toBeInTheDocument();
    });
});