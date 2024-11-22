export interface NavbarPlugin {
  setup(options: { title: string, leftIcon: string, leftVisibility:boolean, rightIcon: string, rightVisibility: false }): Promise<boolean>;
  setTitle(options: { value: string }): Promise<boolean>;
  setLeftIcon(options: { normal: string }): Promise<boolean>;
  setLeftVisibility(options: { value: boolean }): Promise<boolean>;
  setRightIcon(options: { normal: string }): Promise<boolean>;
  setRightVisibility(options: { value: boolean }): Promise<boolean>;
  allowsBackForwardNavigationGestures(options: { value: boolean }): Promise<boolean>;
  exitApp(): Promise<boolean>;
}
