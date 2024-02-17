module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'jest-environment-jsdom',
    transform: {
        '^.+\\.tsx?$': 'ts-jest',
        '^.+\\.css$': 'jest-css-modules-transform',
    },
    testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
    moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
    moduleNameMapper: {
        '^@/(.*)$': '<rootDir>/src/$1',
        '^.+\\.module\\.(css|sass|scss)$': 'identity-obj-proxy',
    },
    collectCoverageFrom: [
        'src/**/*.{ts,tsx}',
        '!**/node_modules/**',
        '!**/vendor/**',
    ],
    coveragePathIgnorePatterns: ['/node_modules/', 'src/index.tsx', 'src/serviceWorker.ts'],
    testPathIgnorePatterns: ['/node_modules/'],
    transformIgnorePatterns: ['<rootDir>/node_modules/'],
};