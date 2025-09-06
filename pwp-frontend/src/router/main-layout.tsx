import * as React from 'react';
import { Suspense } from 'react';
import { Outlet } from 'react-router';
import { AppFooter, AppHeader, SuspenseFallback } from '@/component';

const MainLayout: React.FC = (): React.ReactElement => (
  <Suspense fallback={<SuspenseFallback />}>
    <AppHeader />
    <Outlet />
    <AppFooter />
  </Suspense>
);

export { MainLayout };
