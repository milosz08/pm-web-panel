import * as React from 'react';
import { lazy } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { MainLayout } from '@/router/main-layout.tsx';

const LoginPage = lazy(() => import('@/page/login-page'));
const RootPage = lazy(() => import('@/page/root-page'));
const NotFoundPage = lazy(() => import('@/page/not-found-page'));

const router = createBrowserRouter([
  {
    path: '/',
    element: <MainLayout />,
    children: [
      { path: '/', element: <RootPage /> },
      { path: '/login', element: <LoginPage /> },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
]);

const AppRouter: React.FC = (): React.ReactElement => (
  <RouterProvider router={router} />
);

export { AppRouter };
