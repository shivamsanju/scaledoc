import fetcher from '@/lib/utils/fetcher'
import { CreateAssetData } from '@/types/assets'
import { FileWithPath } from '@mantine/dropzone'


export const createAssetApi = async (
  projectId: string,
  assetData: CreateAssetData,
  file: FileWithPath,
) => {
  const formData = new FormData()
  formData.append("asset", new Blob([JSON.stringify(assetData)], { type: "application/json" }));
  formData.append(`file`, file)
  const res = await fetcher.post(
    `${process.env.NEXT_PUBLIC_BACKEND_URI}/projects/${projectId}/assets`,
    {},
    {
      body: formData,
    }
  )
  if (!res.ok) {
    throw Error(await res.text())
  }
  const asset = await res.json()
  return asset
}


export const getAssetsApi = async (
  projectId: string,
  start: number = 0,
  end: number = 10
) => {
  const res = await fetcher.get(
    `${process.env.NEXT_PUBLIC_BACKEND_URI}/projects/${projectId}/assets?start=${start}&end=${end}`
  )
  if (!res.ok) {
    throw Error(await res.text())
  }
  const assets = await res.json()
  return assets
}


export const deleteAssetApi = async (projectId: string, assetId: string) => {
  const res = await fetcher.delete(`${process.env.NEXT_PUBLIC_BACKEND_URI}/projects/${projectId}/assets/${assetId}`)
  if (!res.ok) {
    throw Error(await res.text())
  }
}



/* ------------------------ OLD APIS *----------------------- */


export const getAssetMemebersApi = async (
  projectId: string,
  assetId: string
) => {
  const res = await fetcher.get(
    `/api/projects/${projectId}/assets/${assetId}/members`
  )
  const resData = await res.json()
  if (!resData.success) {
    throw Error(resData.error)
  }
  return resData.data
}

export const addMemberToAssetApi = async (
  projectId: string,
  assetId: string,
  userId: string,
  role: string
) => {
  const res = await fetcher.post(
    `/api/projects/${projectId}/assets/${assetId}/members`,
    {
      userId: userId,
      role: role,
    },
    {
      headers: {
        'Content-Type': 'application/json',
      },
    }
  )
  const resData = await res.json()
  if (!resData.success) {
    throw Error(resData.error)
  }
  return resData.data
}

export const removeMemberFromAssetApi = async (
  projectId: string,
  assetId: string,
  userId: number
) => {
  const res = await fetcher.delete(
    `/api/projects/${projectId}/assets/${assetId}/members`,
    {
      userId: userId,
    },
    {
      headers: {
        'Content-Type': 'application/json',
      },
    }
  )
  const resData = await res.json()
  if (!resData.success) {
    throw Error(resData.error)
  }
  return resData.data
}
