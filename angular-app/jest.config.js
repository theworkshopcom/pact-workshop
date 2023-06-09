module.exports = {
    testEnvironment: 'jsdom',
    preset: 'jest-preset-angular',
    setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
    testMatch: ['**/+(*.)+(spec).(pact).(ts)'],
    modulePathIgnorePatterns: ["<rootDir>/pacts/"]
};
