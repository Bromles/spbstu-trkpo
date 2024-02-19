import { defineConfig } from 'vitest/config';
import * as path from "path";

export default defineConfig({
    test: {
        globals: true,
        environment: 'jsdom',
        setupFiles: ['./src/tests/vitest.setup.ts'],
        alias: {
            '@': path.resolve(__dirname, './src'),
        },
    },
});