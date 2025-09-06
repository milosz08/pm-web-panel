import React from 'react';
import ReactDOM from 'react-dom/client';
import { AppRouter } from '@/router/app-router.tsx';
import { MuiThemeSupplier } from '@/util/mui-theme-supplier.tsx';

const rootElement = document.getElementById('app-mount') as HTMLElement;
const root = ReactDOM.createRoot(rootElement);

root.render(
  <React.StrictMode>
    <MuiThemeSupplier>
      <AppRouter />
    </MuiThemeSupplier>
  </React.StrictMode>
);
