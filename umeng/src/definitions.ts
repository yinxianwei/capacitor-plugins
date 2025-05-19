export interface UmengPlugin {
  initWithOptions(): Promise<{ data: boolean }>;
  setLogEnabled(options: { value: boolean }): Promise<{ data: boolean }>;
}
