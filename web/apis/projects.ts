import fetcher from '@/lib/utils/fetcher'
import { CreateProjectData } from '@/types/projects'


export const getProjectsApi = async () => {
  const res = await fetcher.get(
    `${process.env.NEXT_PUBLIC_BACKEND_URI}/projects`
  )
  if (!res.ok) {
    throw Error(await res.text())
  }
  const projects = await res.json()
  return projects
}


export const getProjectByIdApi = async (projectId: string) => {
  const res = await fetcher.get(
    `${process.env.NEXT_PUBLIC_BACKEND_URI}/projects/${projectId}`
  )
  if (!res.ok) {
    throw Error(await res.text())
  }
  const project = await res.json()
  return project
}


export const createProjectApi = async (data: CreateProjectData) => {
  const res = await fetcher.post(
    `${process.env.NEXT_PUBLIC_BACKEND_URI}/projects`,data, {
      headers: {
        'Content-Type': 'application/json',
      },
    }
  )
  if (!res.ok) {
    throw Error(await res.text())
  }
  const project = await res.json()
  return project
}


export const deleteProjectApi = async (projectId: string) => {
  const res = await fetcher.delete(`${process.env.NEXT_PUBLIC_BACKEND_URI}/projects/${projectId}`)
  if (!res.ok) {
    throw Error(await res.text())
  }
}


/* ------------------------ OLD APIS *----------------------- */






export const getProjectAdminsByIdApi = async (projectId: string) => {
  const res = await fetcher.get(`/api/projects/${projectId}/admins`)
  const resData = await res.json()
  if (!resData.success) {
    throw Error(resData.error)
  }
  return resData.data
}

export const addAdminToprojectApi = async (
  projectId: string,
  userId: string
) => {
  const res = await fetcher.post(
    `/api/projects/${projectId}/admins`,
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

export const removeAdminFromprojectApi = async (
  projectId: string,
  userId: number
) => {
  const res = await fetcher.delete(
    `/api/projects/${projectId}/admins`,
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
