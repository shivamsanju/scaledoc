import { AssetType } from '@/types/assets'
import { User } from '@/types/users'
import {
  ComboboxItem,
  FileInput,
  OptionsFilter,
  Select,
  Textarea,
  TextInput,
} from '@mantine/core'
import { IconUserCircle } from '@tabler/icons-react'
import { FC } from 'react'

type ISubFormProps = {
  form: any
  assetTypes: AssetType[]
  users: User[]
  projectId: string
}

const MetadataForm: FC<ISubFormProps> = ({ form, assetTypes, users, projectId }) => {
  const optionsFilter: OptionsFilter = ({ options, search }) => {
    const splittedSearch = search.toLowerCase().trim().split(' ')
    return (options as ComboboxItem[]).filter((option) => {
      const words = option.value.toLowerCase().trim().split(' ')
      return splittedSearch.every((searchWord) =>
        words.some((word) => word.includes(searchWord))
      )
    })
  }

  return (
    <>
      <FileInput
        withAsterisk
        size="xs"
        mb="md"
        label="Upload Asset"
        placeholder="Upload ypour file here"
        {...form.getInputProps('file')}
      />

      <TextInput
        withAsterisk
        label="Name"
        size="xs"
        mb="md"
        placeholder="Name is required"
        {...form.getInputProps('name')}
      />


      <Textarea
        label="Description"
        mb="md"
        size="xs"
        rows={4}
        placeholder="Please enter a short description for this asset"
        {...form.getInputProps('description')}
      />

      <TextInput
        label="Tags"
        mb="md"
        size="xs"
        placeholder="Enter tags asscoiated with this asset (comma-separated)"
        {...form.getInputProps('tags')}
      />

      <Select
        leftSection={<IconUserCircle size={20} />}
        label="Authors"
        mb="md"
        size="xs"
        placeholder="Select the authors of this asset"
        filter={optionsFilter}
        searchable
        {...form.getInputProps('authors')}
        data={users.map((e) => ({
          value: e.name.toString(),
          label: e.name,
        }))}
      />
    </>
  )
}

export default MetadataForm
