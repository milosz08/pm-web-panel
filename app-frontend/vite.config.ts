import path from 'path';
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  build: {
    outDir: 'target/dist',
  },
  server: {
    // add proxy only for development (separated vite dev server)
    proxy:
      process.env.NODE_ENV === 'development'
        ? {
            '/api': process.env.VITE_PROXY_API_URL,
          }
        : {},
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
});
