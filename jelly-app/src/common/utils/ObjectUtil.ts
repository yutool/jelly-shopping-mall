export function copyOf(obj: any) {
  return JSON.parse(JSON.stringify(obj))
}
