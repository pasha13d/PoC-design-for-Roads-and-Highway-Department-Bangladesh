interface NavAttributes {
  [propName: string]: any;
}
interface NavWrapper {
  attributes: NavAttributes;
  element: string;
}
interface NavBadge {
  text: string;
  variant: string;
}
interface NavLabel {
  class?: string;
  variant: string;
}

export interface NavData {
  name?: string;
  url?: string;
  icon?: string;
  badge?: NavBadge;
  title?: boolean;
  children?: NavData[];
  variant?: string;
  attributes?: NavAttributes;
  divider?: boolean;
  class?: string;
  label?: NavLabel;
  wrapper?: NavWrapper;
}

export const navItems: NavData[] = [
  {
    name: 'Dashboard_TopMenu',
    url: '/dashboard',
    icon: 'icon-speedometer',
    // badge: {
    //   variant: 'info',
    //   text: 'NEW'
    // }
  },
  {
    name: 'Registration_TopMenu',
    url: '/registration/registration-list',
    icon: 'fa fa-registered'
  },
  {
    name: 'Requisition_TopMenu',
    url: '/requisition',
    icon: 'fa fa-dollar',
    children: [
      {
        name: 'road',
        url: '/requisition/road',
        icon: 'fa fa-list'
      }
    ]
  },
  {
    divider: true
  }
];
