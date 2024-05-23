export interface RoleDetail {
  canView?: boolean;
  canCreate?: boolean;
  canRemove?: boolean;
  canUpdate?: boolean;
  canStatistics?: boolean;
  module?: {
    id?: number;
    moduleName?: string;
  };
}
export interface Roles {
  id?: string;
  name?: string;
  description?: string;
  roleDetails?: RoleDetail[];
}
