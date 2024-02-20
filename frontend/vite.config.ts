/// <reference types="vitest" />
/// <reference types="vite/client" />

import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import * as path from "path";

export default defineConfig({
  plugins: [react()],
  server: {
    port: 4200,
  },
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "./src/test/setup.ts",
    // you might want to disable it, if you don't have tests that rely on CSS
    // since parsing CSS is slow
    css: false,
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
    coverage: {
      exclude: [
        "coverage/**",
        "dist/**",
        "**/[.]**",
        "packages/*/test?(s)/**",
        "**/*.d.ts",
        "**/virtual:*",
        "**/__x00__*",
        "**/\x00*",
        "cypress/**",
        "test?(s)/**",
        "test?(-*).?(c|m)[jt]s?(x)",
        "**/*{.,-}{test,spec}.?(c|m)[jt]s?(x)",
        "**/__tests__/**",
        "**/{karma,rollup,webpack,vite,vitest,jest,ava,babel,nyc,cypress,tsup,build}.config.*",
        "**/vitest.{workspace,projects}.[jt]s?(on)",
        "**/.{eslint,mocha,prettier}rc.{?(c|m)js,yml}",
        "**/*.cjs",
        "src/test",
        "src/mocks",
        "src/main.tsx",
        "**/*.html",
        "**/*.md",
        "**/*.css",
        "tsconfig.json",
        "tsconfig.node.json",
        "src/components/HospitalMap/*",
        "src/utils/apiUtils.ts",
        "src/utils/oidcConfig.ts",
        "src/utils/types.ts",
        "src/utils/Router.tsx",
      ],
    },
  },
  resolve: {
    alias: {
      "@": "/src",
    },
  },
});
