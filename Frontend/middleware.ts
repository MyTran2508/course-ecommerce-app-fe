import { DataResponse } from './types/response.type';
import { NextResponse } from 'next/server'
import { NextRequest } from 'next/server'
import { AuthState } from './redux/features/authSlice';
import { User} from './types/user.type';
import * as _ from "lodash"
import { Role } from './utils/resources';

const userRoutes = ['/cart', '/my-courses', '/payment/checkout', '/learning', '/user','/courses'];

const isAdminRoute = (pathname: string) => {
    return pathname.startsWith('/admin');
}

const isUserRoute = (pathname: string) => {
  return userRoutes.some((route) => pathname.startsWith(route));
};
 
export async function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;
  const loginUrl = new URL(`/login`, request.nextUrl.origin); 
  const homeUrl = new URL(`/`, request.nextUrl.origin);
  const adminUrl = new URL(`/admin`, request.nextUrl.origin);

  let test = request.cookies.get("user"); 
  let data: AuthState = test ? JSON.parse(test?.value as string) : {}

  if ((data as AuthState).username) {
    const user: User = await fetch(`http://localhost:8082/api/users/user/get-by-username/${data.username}`)
    .then(response => response.json())
      .then((data: DataResponse) => {
        return data.data as User
      })
    const role = user.roles && user.roles.length > 0 ? user.roles[1]?.name : null;

    if ((isUserRoute(pathname) && _.isEqual(role, Role.USER) )
      || (isAdminRoute(pathname) && _.isEqual(role, Role.ADMIN))) {
      
      return NextResponse.next();
    }

    return NextResponse.redirect(loginUrl)
  }

  return NextResponse.redirect(loginUrl)
}
 
export const config = {
  matcher: ['/cart','/my-courses','/payment/checkout', '/learning/:path*','/user/:path*','/courses/:path*'],
}